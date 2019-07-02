/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { DominioSkillDeleteDialogComponent } from 'app/entities/dominio-skill/dominio-skill-delete-dialog.component';
import { DominioSkillService } from 'app/entities/dominio-skill/dominio-skill.service';

describe('Component Tests', () => {
  describe('DominioSkill Management Delete Component', () => {
    let comp: DominioSkillDeleteDialogComponent;
    let fixture: ComponentFixture<DominioSkillDeleteDialogComponent>;
    let service: DominioSkillService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [DominioSkillDeleteDialogComponent]
      })
        .overrideTemplate(DominioSkillDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DominioSkillDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DominioSkillService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
